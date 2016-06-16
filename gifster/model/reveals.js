var mongoose = require("mongoose");
var mongoSchema =   mongoose.Schema;

var reveals = {
	"userid":String,
	"revealed_profiles":String
}
module.exports = mongoose.model('reveals',reveals);
