const express = require('express');
const router = express.Router();
var braintree = require("braintree");
const authenticateToken = require('../middleware/authenticateToken')

var gateway = braintree.connect({
    environment:  braintree.Environment.Sandbox,
    merchantId:   'k6ktgv22dkbhn6sk',
    publicKey:    'mzw38b4fpm8zh2gg',
    privateKey:   '92b1a7eb2a8c9149f72fcdb581a7c56a'
});


/*create token for client before payment

router.get("/getClientToken", auth, async (req, res)=> {
try {
    gateway.generate({customerId: req.user._id}, function(err, res){
        if(result.success){
            res.status(200).send({token: result.clientToken})
        }else{
            res.status(400).send({message: "something wrong"});
        }
    });
} catch (error) {
    res.status(400).send(error);
}

}) */

//send the id that we searched from the jwt
router.post('/registerBrainTree', authenticateToken, async (req, res) =>{
    res.json({
        posts: {
            title: "my first post",
            description: "random data here"
        }
    });
})

module.exports = router; 