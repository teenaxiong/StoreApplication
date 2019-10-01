//validation
const Joi = require('@hapi/joi'); 

//register validation
const registerValidation = (data) => {
    const schema = {
        fname: Joi.string().min(6).required(),
        lname: Joi.string().min(6).required(), 
        email: Joi.string().min(6).required().email(),
        password01: Joi.string().min(6).required(),
        password02: Joi.string().min(6).required().equal(password01)
    }; 
    return Joi.validate(data, schema);
}; 

//login validation
const loginValidation = (data) => {
    const schema = {
        email: Joi.string().min(6).required().email(),
        password: Joi.string().min(6).required()
    }; 
    return Joi.validate(data, schema);
}; 

module.exports.registerValidation = registerValidation; 
module.exports.loginValidation = loginValidation; 
