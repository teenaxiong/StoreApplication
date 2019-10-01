const router = require('express').Router();
const User = require('../model/User'); 
const {registerValidation, loginValidation} = require('../validation'); 
const bcrypt = require('bcryptjs'); 
const jwt = require('jsonwebtoken'); 


router.post('/register',  async (req, res) => {
    //validates data before making user
    const{error} = registerValidation(req.body);
    if(error) return res.status(400).send(error.details[0].message);

    if(req.body.password01 != req.body.password02) return res.status(401).send("Password does not match"); 

    //checking if user is in database
    const emailExist = await User.findOne({email: req.body.email});
    if(emailExist) return res.status(400).send('Email already exists');
    
    //hash password
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(req.body.password01, salt);


    //create a new user
    const user = new User({
        fname: req.body.fname,
        lname: req.body.lname,
        email: req.body.email,
        password: hashedPassword
    }); 
    try{
        const savedUser = await user.save(); 
        res.send({user: user._id}); 
    }catch(err){
        res.status(400).send(err); 
    }

}); 

//login
router.post('/login', async (req, res) => {
    //lets validate the data
    const{error} = loginValidation(req.body);
    if(error) return res.status(400).send(error.details[0].message);

    //checking if the email exist
    const user = await User.findOne({email: req.body.email});
    if(!user) return res.status(400).send("Email doesn't exist"); 

    //if password is correct
    const validPass = await bcrypt.compare(req.body.password, user.password);
    if(!validPass) return res.status(400).send("Invalid password.");

    //create and assigne a token
    const token  = jwt.sign({_id: user._id}, process.env.TOKEN_SECRET);
    res.header('auth-token', token).send(token); 
    

})


module.exports = router; 