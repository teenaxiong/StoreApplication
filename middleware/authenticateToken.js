const jwt = require('jsonwebtoken');


function autheticateToken(req, res, next){
    const bearerHeader = req.headers['authorization']; 

    if(typeof bearerHeader !== 'undefined'){
        const bearer = bearerHeader.split(" ");
        const bearerToken = bearer[1];

 
        jwt.verify(bearerToken, process.env.TOKEN_SECRET, function(err, user){
            if(err) return res.sendStatus(403)
            req.user = user
            next()
        })
    }else{
        res.sendStatus(403); 
    }
 
}

module.exports = autheticateToken; 