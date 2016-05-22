

var express = require("express");
var app = express();
var http = require('http').Server(app);
var bodyParser  =   require("body-parser");
var gifsaving     =   require("./model/gifs");
var users     =   require("./model/users");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({"extended" : false}));



app.post("/savegif",function(req,res){

    var gif_url = req.query.gifurl;
    var person_id = req.query.id;
    var response={};
    gifsaving.findOne({"userid":person_id},function(err,data){
              if(err) {
                  response = {"error" : true,"message" : "Error fetching data"};
                  res.send(JSON.stringify(response));
              } else {
                if(data){
                  var tempString = data.gif_urls;
                  var tempArray = JSON.parse(tempString);

                  if(tempArray["urlist"].indexOf(gif_url)>-1){
                    response = {"error" : true,"message" : "Already on your list!"};
                    res.send(JSON.stringify(response));
                  }else{
                    tempArray["urlist"].push(gif_url);
                    data.gif_urls=JSON.stringify(tempArray);
                    data.save(function(err,user){
                        if(err) {
                            response = {"error" : true,"message" : "Error adding data"};
                        } else {
                          console.log("saved");
                            response = {"error" : false,"message" : "Added to keyboard"};
                        }
                        res.send(JSON.stringify(response));
                    });
                  }
                }else{
                  var db = new gifsaving();
                  var tempArray = {"urlist":[]};
                  tempArray["urlist"].push(gif_url);
                  console.log("notexist");
                  db.gif_urls=JSON.stringify(tempArray);
                  db.userid = person_id;
                  db.save(function(err,user){
                      if(err) {
                        response = {"error" : true,"message" : "Error adding data"};
                      } else {
                        console.log("saved");
                        response = {"error" : false,"message" : "Added to keyboard"};
                      }
                      res.send(JSON.stringify(response));
                  });
                }
              }
          });
});
app.get("/getgifs",function(req,res){
    var person_id = req.query.id;
    gifsaving.findOne({"userid":person_id},function(err,data){
        if(err){
        response = {"error" : true,"message" : "Error fetching data"};
        res.send(JSON.stringify(response));
        }else{
        if(data){
        var tempString = data.gif_urls;
        res.send(tempString);
        }
        }

    });

});
app.post("/registeruser",function(req,res){
    var person_id = req.query.id;
    var lat = req.query.lat;
    var long = req.query.long;

    users.findOne({"userid":person_id},function(err,data){
        if(err){
          response = {"error" : true,"message" : "Error fetching data"};
          res.send(JSON.stringify(response));
        }else{
          if(data){
            data.location = lat+"-"+long;
            data.save(function(err,user){
            if(err) {
              response = {"error" : true,"message" : "Error adding data"};
              } else {
              response = {"error" : false,"message" : "Location updated: " +user.location};
              }
              res.send(JSON.stringify(response));
              });
          }else{
           var db = new users();
           db.userid = person_id;
           db.location = lat+"-"+long;
           db.likes = "{}";
           db.dislikes = "{}";
           db.save(function(err,user){
              if(err) {
                  response = {"error" : true,"message" : "Error adding data"};
                } else {
                 response = {"error" : false,"message" : "New user added location : "+user.location};
              }
              res.send(JSON.stringify(response));
           });
          }
        }

    });
});

app.get("/deletegif",function(req,res){
    var person_id = req.query.id;
    var url = req.query.url;

    gifsaving.findOne({"userid":person_id},function(err,data){
        if(err){
          response = {"error" : true,"message" : "Error fetching data"};
          res.send(JSON.stringify(response));
        }else{
          var tempString = data.gif_urls;
          var tempArray = JSON.parse(tempString);
          var index =tempArray["urlist"].indexOf(url);
          if(index != -1)
              tempArray["urlist"].splice(index,1);
          data.gif_urls=JSON.stringify(tempArray);
          data.save(function(err,user){
              if(err) {
                response = {"error" : true,"message" : "Error adding data"};
              } else {
                response = {"error" : false,"message" : "Deleted from GIFBoard"};
              }
              res.send(JSON.stringify(response));
          });
        }
    });


});


http.listen(3000, function(){
  console.log('listening on *:3000');
});
