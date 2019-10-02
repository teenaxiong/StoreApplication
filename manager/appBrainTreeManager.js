const braintree = require("braintree");

var gateway = braintree.connect({
    environment:  braintree.Environment.Sandbox,
    merchantId:   'k6ktgv22dkbhn6sk',
    publicKey:    'mzw38b4fpm8zh2gg',
    privateKey:   '92b1a7eb2a8c9149f72fcdb581a7c56a'
});

class AddBraintreeManager{
    createCustomer(register){
        return gateway.customer.create({
            id: register.userId,
            email: register.email
        }).then(result =>{
            return result.success;
        }).catch(error =>{
            return result.error;
        })
    }
}

module.exports = AppBraintreeManager; 