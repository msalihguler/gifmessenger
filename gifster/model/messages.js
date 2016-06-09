var mongoose = require("mongoose");
var mongoSchema =   mongoose.Schema;

var messages = {
	"chat_id":String,
	"sender_id":String,
    "message":String
}
module.exports = mongoose.model('messages',messages);
