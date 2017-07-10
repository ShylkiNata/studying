var express = require('express'); 

var cors = require('cors');
var app = express();
app.use(cors());

var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const pgp = require('pg-promise')();
const cn = "postgres://shana:shana@localhost:5432/shana";
const db = pgp(cn);

var fs = require('fs');
var path = require('path');

var email="", password="", action, role="", additional="", avatar="", location="", img="", ext=""; // request body

var answer;

app.post('/', function(req, res){
    action=req.body['action'];

    if(action==1 || action==2) {
        email=req.body['email'];
        password=req.body['password'];
    }

    answer = { success: '', error:''}

    switch(action) {
        case 1: 
            authorization(res);
            break;
        case 2: 
            registration(req,res);  // C
            break;
        case 3: 
            selection(res);         // R           
            break;
        case 4: 
            deletion(req,res);      // D
            break;
        case 5:
            renovation(req,res);    // U
            break;
        case 6:
            findRoutes(req,res);    // select trails
            break;
    }
});

const port=8080;
app.listen(port,function(){
  console.log("Server is started on port: "+port);
});


function authorization(res){
    try {
        db.one('SELECT role,avatar FROM userdata WHERE email = $1 AND password = $2', [email, password])
          .then((result) => {  
   
            img = fs.readFileSync('avatar/'+email+'.'+result['avatar']);
            result['avatar']='data:image/'+result['avatar']+';base64,'+new Buffer(img).toString('base64');
            answer['success']=result;
            res.send(answer);
          })
          .catch((error) => {
            answer['error']="User with such password not found";
            console.log(error);
            res.send(answer);
          });
    } 
    catch(error) {
        answer['error']=error;
        res.send(answer);
    }
}

// INSERT INTO userdata(email, password, role, avatar) VALUES('leonatashka@shana.com','shana','admin','png')
function registration(req,res){
    try {
        role=req.body['role'];
        avatar=req.body['avatar'];

        img = avatar.replace(/^data:image\/\w+;base64,/, "");
        ext = avatar.substring(avatar.indexOf('/')+1,avatar.indexOf(';'));

        db.none('INSERT INTO userdata(email, password, role, avatar) VALUES($1, $2, $3, $4)', [email, password, role, ext])
          .then(() => {
                answer['success']=role;
                fs.writeFile("avatar/"+email+"."+ext, new Buffer(img, 'base64'));
                res.send(answer);
           })
          .catch((error) => {
                answer['error']="An error occurred when adding a user.";
                res.send(answer);
           });  
    }
    catch(error) {
        answer['error']=error;
        res.send(answer);
    }
}

function selection(res){
    try {
        db.any('SELECT email,password,role,avatar FROM userdata')
          .then((result) => {

                for (var i in result) {
                    img = fs.readFileSync('avatar/'+result[i]['email']+'.'+result[i]['avatar']);
                    result[i]['avatar']='data:image/'+result[i]['avatar']+';base64,'+new Buffer(img).toString('base64');
                }

                answer['success']=result;
                res.send(answer);
            })
          .catch((error) => {
                console.log(error);
                answer['error']="Something went wrong. User list can not be returned.";
                res.send(answer);
            });
    }
    catch(error) {
        answer['error']=error;
        res.send(answer);
    }
}

function deletion(req,res){
    try {
    email=req.body['email'];

    db.one('SELECT avatar FROM userdata WHERE email = $1',[email])
          .then((result) => {
                fs.unlinkSync('avatar/'+email+'.'+result['avatar']);

                db.none('DELETE FROM userdata WHERE email = $1', [email])
                  .then((r) => {
                        res.send(answer);
                    })
                  .catch((e) => {
                        answer['error']="User can not be deleted.";
                        res.send(answer);
                    });
            })
          .catch((error) => {
                console.log(error);
                answer['error']="Something went wrong. User list can not be returned.";
                res.send(answer);
            });
    }
    catch(error) {
        answer['error']=error;
        res.send(answer);
    }
}

function renovation(req,res){
    try {
    email=req.body['email'];
    password=req.body['password'];
    role=req.body['role'];
    avatar=req.body['avatar'];
    additional=req.body['additional'];

    img = avatar.replace(/^data:image\/\w+;base64,/, "");
    ext = avatar.substring(avatar.indexOf('/')+1,avatar.indexOf(';'));

    db.none('UPDATE userdata SET email = $1, password = $2, role = $3, avatar = $4 WHERE email = $5', [email, password, role, ext, additional])
      .then((result) => {
            fs.writeFile("avatar/"+email+"."+ext, new Buffer(img, 'base64'));
            res.send(answer);
        })
      .catch((error) => {
            answer['error']="An error occurred when updating a user. It will be eliminated soon.\n Sorry for the inconvenience!";
            res.send(answer);
        });
    }
    catch(error) {
        answer['error']=error;
        res.send(answer);
    }
}

function findRoutes(req,res){
    location='%'+(req.body['location']).toLowerCase()+'%';

    var country='(SELECT name FROM country WHERE LOWER(name) LIKE $1 ORDER BY name)';
    var state='(SELECT name FROM state WHERE LOWER(name) LIKE $1 ORDER BY name)';
    var trail='(SELECT name FROM trail WHERE LOWER(name) LIKE $1 ORDER BY name)';

    db.any(country+' UNION '+state+' UNION '+trail+' LIMIT 5', [location])
      .then((result) => {
            answer['success']=result;
            console.log(result);
            res.send(answer);
        })
      .catch((error) => {
            answer['error']="Trails not found";
            console.log(error);
            res.send(answer);
        });
}