package love.forte.simbot.telegram.stdlib

// More examples in curl:
// See https://core.telegram.org/bots/webhooks#the-verbose-version

const val MESSAGE_WITH_TEXT = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "id":1111111,
     "type": "private",
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "text":"/start"
}
}"""

const val FORWARDED_MESSAGE = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "id":1111111,
     "type": "private",
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "forward_origin": {
     "type": "user",
     "date": 1441645550,
     "sender_user": {
         "last_name":"Forward Lastname",
         "id": 222222,
         "first_name":"Forward Firstname"
     }
  },
  "forward_date":1441645550,
  "text":"/start"
}
}"""

const val FORWARDED_CHANNEL_MESSAGE = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "forward_origin": {
     "type": "channel",
     "date": 1441645550,
     "message_id": 1000000,
     "chat": {
         "type":"channel",
         "id": 222222,
         "title": "Test channel"
     }
  },
  "forward_from": {
     "id": -10000000000,
     "type": "channel",
     "title": "Test channel"
  },
  "forward_date":1441645550,
  "text":"/start"
}
}"""

const val MESSAGE_WITH_A_REPLY = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "text":"/start",
  "reply_to_message":{
      "date":1441645000,
      "chat":{
          "last_name":"Reply Lastname",
          "type": "private",
          "id":1111112,
          "first_name":"Reply Firstname",
          "username":"Testusername"
      },
      "message_id":1334,
      "text":"Original"
  }
}
}"""

const val EDITED_MESSAGE = """{
"update_id":10000,
"edited_message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "text":"Edited text",
  "edit_date": 1441646600
}
}"""

const val MESSAGE_WITH_ENTITIES = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "text":"Bold and italics",
  "entities": [
      {
          "type": "italic",
          "offset": 9,
          "length": 7
      },
      {
          "type": "bold",
          "offset": 0,
          "length": 4
      }
      ]
}
}"""

const val MESSAGE_WITH_AUDIO = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "audio": {
      "file_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "file_unique_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "duration": 243,
      "mime_type": "audio/mpeg",
      "file_size": 3897500,
      "title": "Test music file"
  }
}
}"""

const val VOICE_MESSAGE = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "voice": {
      "file_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "file_unique_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "duration": 5,
      "mime_type": "audio/ogg",
      "file_size": 23000
  }
}
}"""

const val MESSAGE_WITH_A_DOCUMENT = """{
"update_id":10000,
"message":{
  "date":1441645532,
  "chat":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "message_id":1365,
  "from":{
     "last_name":"Test Lastname",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "document": {
      "file_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "file_unique_id": "AwADBAADbXXXXXXXXXXXGBdhD2l6_XX",
      "file_name": "Testfile.pdf",
      "mime_type": "application/pdf",
      "file_size": 536392
  }
}
}"""

const val INLINE_QUERY = """{
"update_id":10000,
"inline_query":{
  "id": 134567890097,
  "from":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "query": "inline query",
  "offset": ""
}
}"""

const val CHOSEN_INLINE_QUERY = """{
"update_id":10000,
"chosen_inline_result":{
  "result_id": "12",
  "from":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "query": "inline query",
  "inline_message_id": "1234csdbsk4839"
}
}"""

const val CALLBACK_QUERY = """{
"update_id":10000,
"callback_query":{
  "id": "4382bfdwdsb323b2d9",
  "from":{
     "last_name":"Test Lastname",
     "type": "private",
     "id":1111111,
     "first_name":"Test Firstname",
     "username":"Testusername"
  },
  "data": "Data from button callback",
  "chat_instance": "aaabbbcccddd",
  "inline_message_id": "1234csdbsk4839"
}
}"""

