import React, { useEffect, useState } from 'react';
import { signUp } from '../service/auth';
import { useNavigate } from 'react-router';
import { useAuth } from '../actions/AuthProvider';

function SignUp() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState(null);
  const [submitted, setSubmitted] = useState(false);
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleName = (event) => {
    setName(event.target.value);
    setSubmitted(false);
  };

  const handleEmail = (event) => {
    setEmail(event.target.value);
    setSubmitted(false);
  };

  const handlePassword = (event) => {
    setPassword(event.target.value);
    setSubmitted(false);
  };

  const handleConfirmPassword = (event) => {
    setConfirmPassword(event.target.value);
    setSubmitted(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (name === '' || email === '' || password === '' || confirmPassword === '') {
      setError(true);
    } else if (password !== confirmPassword) {
      setError(true);
    } else {
      setSubmitted(true);
      setError(false);
      const data = {
        name: name,
        email: email,
        password: password
      }
      const response = await signUp(data);
      // console.log(response);
      // console.log(`Signing up with name: ${name}, email: ${email}, and password: ${password}`);
    }
  };

  useEffect(() => {
    if (user) {
      console.log(user);
      return navigate("/");
    }
  }, [])


  const successMessage = () => {
    return (
      <div
        className="success"
        style={{ display: submitted ? '' : 'none' }}
      >
        <h1>User {name} successfully registered!!</h1>
      </div>
    );
  };

  const errorMessage = () => {
    return (
      <div
        className="error"
        style={{ display: error ? '' : 'none' }}
      >
        <h1>Please enter all the fields or passwords do not match</h1>
      </div>
    );
  };

  return (
    <div className="signup-container">
      <h1>Sign Up</h1>
      <form className='form' onSubmit={handleSubmit}>
        <div className='input-field'>
          <label>Name:</label>
          <input
            type="text"
            value={name}
            onChange={handleName}
            placeholder="John Doe"
          />
        </div>
        <div className='input-field'>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={handleEmail}
            placeholder="example@example.com"
          />
        </div>
        <div className='input-field'>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={handlePassword}
            placeholder="Password"
          />
        </div>
        <div className='input-field'>
          <label>Confirm Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={handleConfirmPassword}
            placeholder="Confirm Password"
          />
        </div>
        {error && errorMessage()}
        {submitted && successMessage()}
        <button className='submit-button' type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default SignUp;