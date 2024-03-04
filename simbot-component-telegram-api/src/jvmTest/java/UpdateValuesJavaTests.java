import love.forte.simbot.telegram.api.update.Update;
import love.forte.simbot.telegram.api.update.UpdateValues;
import love.forte.simbot.telegram.type.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ForteScarlet
 */
public class UpdateValuesJavaTests {

    @Test
    public void resolveTest() {
        // {"update_id": 1, "message": {"message_id": 114}}
        var update = Update.decodeFromRawJson("{\n" +
                                              "\"update_id\": 1,\n" +
                                              "\n" +
                                              "\n" +
                                              "  \"message\": {\n" +
                                              "    \"message_id\": 114,\n" +
                                              "    \"date\": 514,\n" +
                                              "    \"chat\": {" +
                                              "\n" +
                                              "      \"id\": 999,\n" +
                                              "      \"type\": \"channel\"" +
                                              "\n" +
                                              "    }" +
                                              "}}\n");

        Assertions.assertEquals(1, update.getUpdateId());
        final var message = update.getMessage();
        Assertions.assertNotNull(message);
        Assertions.assertEquals(114, message.getMessageId());

//        UpdateValues
        class Values {
            final String name;
            final Object value;

            public Values(String name, Object value) {
                this.name = name;
                this.value = value;
            }
        }

        var resolved = UpdateValues.resolveTo(update, (name, value) -> new Values(name, value));
        Assertions.assertTrue(resolved instanceof Values);
        Assertions.assertEquals(UpdateValues.MESSAGE_NAME, ((Values) resolved).name);
        Assertions.assertTrue(((Values) resolved).value instanceof Message);
    }

}
