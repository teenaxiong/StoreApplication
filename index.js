const express = require('express');
const app = express();
const mongoose = require('mongoose'); 
const dotenv = require('dotenv');


//import routes
const authRoute = require('./routes/auth');
const postRoute = require('./routes/post');
const gateway = require('./routes/braintree')
mongoose.set('useUnifiedTopology', true);
const PORT = process.env.PORT || 3000
dotenv.config(); 

//connect to db
mongoose.connect(process.env.DB_CONNECT, { useNewUrlParser: true } , 
() => console.log('connected to db')); 

//middleware
app.use(express.json());


//route middlerwares
app.use('/api/user', authRoute); 
app.use('/api/post', postRoute); 


app.listen(PORT, () => console.log('Server up and running'));

