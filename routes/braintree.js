const express = require('express');
const router = express.Router();
var braintree = require("braintree");
const authenticateToken = require('../middleware/authenticateToken')
const User = require('../model/User'); 

var gateway = braintree.connect({
    environment:  braintree.Environment.Sandbox,
    merchantId:   'k6ktgv22dkbhn6sk',
    publicKey:    'mzw38b4fpm8zh2gg',
    privateKey:   '92b1a7eb2a8c9149f72fcdb581a7c56a'
});



//authenticateToken will use authenticateToken.js to decode the JWT and return back a 
//user id in the form of req.user
router.get('/customerBrainTree', authenticateToken, async (req, res) =>{
  
  var obj_id = new require('mongodb').ObjectID(req.user._id)//this allows us to get the mongo id
    const user = await User.findOne({_id: obj_id}); //find if there is a user who exist with the id
  if(!user) return res.status(400).send("Error. Please log in first."); 

    gateway.customer.find(req.user._id, function(err, customer){
      if(err){//userID not found
        createCustomer(res, user, req.user._id)
      }else{
        generateClientToken(res, req.user._id)
      }
    }); 

  
})

//start the transaction
router.post('/transaction', function(req, res){
  var nonceFromTheClient = req.body.paymentMethodNonce;
  var paymentAmount = req.body.paymentAmount

  gateway.transaction.sale({
    amount: paymentAmount,
    paymentMethodNonce: nonceFromTheClient,
    options: {
      submitForSettlement: true
    }
  }, function (err, result) {
    if((result.success  || result.transaction)){
      res.status(200).send("Transaction complete")
    } 
    else{
      res.status(400).send("There was an error while completing transaction")
    }
      
    });
})

var generateClientToken = function(res, userId){
  gateway.clientToken.generate({
    customerId: userId
  }, function (err, response){
    if(err){
      res.status(400)
      res.setHeader('Content-Type', 'text/plain')
      res.write("There was an error while getting clientToken")
      res.send()
    }else{
      var clientTokenJson = {
        'clientToken': response.clientToken
    }      
    res.status(200).setHeader('Content-Type', 'application/json')
      res.json(clientTokenJson)
    }
  })
}

var createCustomer = function(res, user, userId){
  gateway.customer.create({
    firstName: user.fname,
    lastName: user.lname,
    email: user.email,
    id: userId
  }, function (err, result) {
    if(err){
      res.status(400)
      res.setHeader('Content-Type', 'text/plain')
      res.write("There was an error while creating customer")
      res.send()
    }else{
      generateClientToken(res, result)
    }
  });

}

module.exports = router; 