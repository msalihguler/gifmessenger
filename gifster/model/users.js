var mongoose = require("mongoose");
var mongoSchema =   mongoose.Schema;

var users = {
	"userid":String,
	"location":String,
    "likes":String,
    "matches":String,
    "token":String,
    "dislikes":String
}
module.exports = mongoose.model('users',users);
