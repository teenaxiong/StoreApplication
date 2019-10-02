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

//authenticateToken will use authenticateToken.js to decode the JWT and return back a 
//user id in the form of req.user._id
router.get('/registerBrainTree', authenticateToken, async (req, res) =>{
    res.json({
            userID: req.user._id
    });

    const user = await User.findOne({_id: req.user._id}); 
    if(!user) return res.status(400).send("Error. Please log in first."); 

    gateway.customer.create({
        firstName: req.user.firstName,
        lastName: req.user.lastName,
        email: req.user.email,
        id: req.user._id
      }, function (err, result) {
        result.success;
        // true
      
        result.customer.id;
        // e.g. 494019
      });

})

module.exports = router; 