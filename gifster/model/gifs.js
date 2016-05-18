var mongoose = require("mongoose");
mongoose.connect('mongodb://localhost:27017/gifster');

var mongoSchema =   mongoose.Schema;

var gifs = {
	"userid":String,
  "gif_urls":String
}
module.exports = mongoose.model('gifs',gifs);
