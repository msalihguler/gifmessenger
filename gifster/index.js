

var express = require("express");
var app = express();
var http = require('http').Server(app);
var bodyParser  =   require("body-parser");
var gifsaving     =   require("./model/gifs");
var users     =   require("./model/users");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({"extended" : false}));


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
           db.likes = "[]";
           db.dislikes = "[]";
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
app.get("/getpeople",function(req,res){
    var person_id = req.query.id;
    users.findOne({"userid":person_id},function(err,data){
        if(err){
        response = {"error" : true,"message" : "Error adding data"};
        res.send(JSON.stringify(response));
        }else{
        if(data){
                var templikes = JSON.parse(data.likes);
                var tempdislikes = JSON.parse(data.dislikes);
                var finalArray = templikes.concat(tempdislikes);
                finalArray.push(person_id);
                console.log(finalArray);
               users.find({"userid":{"$nin":finalArray}},function(e,d){
                    if(err){
                        response = {"error" : true,"message" : "Error adding data"};
                        res.send(JSON.stringify(response));
                    }else{
                      if(d){
                        response = {"error" : false,"message" : JSON.stringify(d)};
                        res.send(JSON.stringify(response));
                        }
                    }

               });
            }
        }
    });
});
app.get("/sendlikestatus",function(req,res){
    var my_id = req.query.m_id;
    var o_id = req.query.o_id;
    var type = req.query.type;
    var match = false;
    if(type=="like"){
        users.findOne({"userid":o_id},function(err,data){
               if(err){
                 response = {"error" : true,"message" : "Error fetching data"};
                 res.send(JSON.stringify(response));
               }else{
                    var tempArray = JSON.parse(data.likes);
                    if(tempArray.indexOf(m_id)>-1){
                        match = true;
                    });
                    users.findOne({"userid":my_id},function(error,d){
                    if(error){
                     response = {"error" : true,"message" : "Error fetching data"};
                     res.send(JSON.stringify(response));
                    }else{
                        var tempLikes = JSON.parse(d.likes);
                        templikes.push(o_id);
                        d.likes = JSON.stringify(templikes);
                        d.save(function(err,user){
                        if(err) {
                           response = {"match" : false,"message" : "Error adding data"};
                           } else {
                           response = {"match" : match,"message" : "likes updated: " +user.likes};
                           }
                           res.send(JSON.stringify(response));
                        });
                    }

                    });
               }
               });
    }else if(type=="dislike"){
         users.findOne({"userid":my_id},function(error,d){
                            if(error){
                             response = {"error" : true,"message" : "Error fetching data"};
                             res.send(JSON.stringify(response));
                            }else{
                                var tempLikes = JSON.parse(d.dislikes);
                                templikes.push(o_id);
                                d.dislikes = JSON.stringify(templikes);
                                d.save(function(err,user){
                                if(err) {
                                   response = {"error" : true,"message" : "Error adding data"};
                                   } else {
                                   response = {"error" : false,"message" : "DisLikes updated: " +user.dislikes};
                                   }
                                   res.send(JSON.stringify(response));
                                });
                            }

                            });
    }
});


http.listen(3000, function(){
  console.log('listening on *:3000');
});
