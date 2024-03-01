import java.util.*
import kotlin.test.Test


class Gen {
    private val s = """
        User
        This object represents a Telegram user or bot.

        Field	Type	Description
        id	Integer	Unique identifier for this user or bot. This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
        is_bot	Boolean	True, if this user is a bot
        first_name	String	User's or bot's first name
        last_name	String	Optional. User's or bot's last name
        username	String	Optional. User's or bot's username
        language_code	String	Optional. IETF language tag of the user's language
        is_premium	True	Optional. True, if this user is a Telegram Premium user
        added_to_attachment_menu	True	Optional. True, if this user added the bot to the attachment menu
        can_join_groups	Boolean	Optional. True, if the bot can be invited to groups. Returned only in getMe.
        can_read_all_group_messages	Boolean	Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
        supports_inline_queries	Boolean	Optional. True, if the bot supports inline queries. Returned only in getMe.
    """.trimIndent()

    data class Field(val name: String, val rawName: String, val type: String, val def: String?, val comment: String)

    @Test
    fun gen() {
        val name = "User"
        val comment = "This object represents a Telegram user or bot."


        val fields = s.lineSequence()
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .map { it.split("\t", limit = 3) }
            .drop(3)
            .map(::toField)
            .toList()

        println("/**")
        println(" * [${name}](https://core.telegram.org/bots/api#${name.lowercase()})")
        println(" * ")
        comment.lineSequence().forEach {
            println(" * $it")
        }
        println(" * ")
        fields.forEach {
            println(" * @property ${it.name} ${it.comment}")
        }
        println(" * ")
        println(" * @author ForteScarlet")
        println(" */")
        println("@Serializable")
        println("public data class $name(")
        fields.forEach {
            if (it.rawName != it.name) {
                println("    @SerialName(\"${it.rawName}\")")
            }
            println("    val ${it.name}: ${it.type}${if (it.def != null) " = ${it.def}" else ""},")
        }
        println(")")


    }

    private fun toField(line: List<String>): Field {
        var fieldDef: String? = null

        val fieldRawName = line[0]
        val fieldRawType = line[1]
        val fieldRawComm = line[2].trim()
        val fieldTypeOptional = fieldRawComm.startsWith("Optional", true)
        val fieldTypeOpEnd = if (fieldTypeOptional) {
            fieldDef = "null"
            "?"
        } else ""

        val fieldName = buildString(fieldRawName.length) {
            fieldRawName.split('_').forEachIndexed { index, s ->
                if (index == 0) {
                    append(s)
                } else {
                    append(s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
            }
        }

        val type = when (fieldRawType) {
            "Integer" -> if (fieldRawComm.contains("64-bit integer", true)) "Long$fieldTypeOpEnd" else "Integer$fieldTypeOpEnd"
            "True" -> {
                fieldDef = "true"
                "Boolean$fieldTypeOpEnd"
            }
            "False" -> {
                fieldDef = "false"
                "Boolean$fieldTypeOpEnd"
            }
            else -> fieldRawType + fieldTypeOpEnd
        }

        return Field(name = fieldName, rawName = fieldRawName, type = type, def = fieldDef, comment = fieldRawComm)
    }

}
