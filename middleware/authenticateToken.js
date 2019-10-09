const jwt = require('jsonwebtoken');


function autheticateToken(req, res, next){
    const bearerHeader = req.headers['authorization']; 

    if(typeof bearerHeader !== 'undefined' && bearerHeader.startsWith("Bearer ")){
        const bearer = bearerHeader.split(" ");
        const bearerToken = bearer[1];

 
        jwt.verify(bearerToken, process.env.TOKEN_SECRET, function(err, user){
            if(err) return res.status(403).send("JWT verification failed")
            req.user = user
            next()
        })
    }else{
        res.status(403).send("JWT verification failed"); 
    }
 
}

module.exports = autheticateToken; 