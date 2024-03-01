package gen


/**
 *
 * @author ForteScarlet
 */
data class Type(
    val name: String,
    val commentLines: List<String>,
    val fieldLines: List<String>,
    val file: String,
)
