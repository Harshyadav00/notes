import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { useAuth } from '../actions/AuthProvider';

function SignIn() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { signIn, user } = useAuth();


  const handleSubmit = async (event) => {
    event.preventDefault();

    const data = {
      email: email,
      password: password
    }

    signIn(data);

  };

  useEffect(() => {
    
    if (user) {
      return navigate("/");
    }
  }, [user])


  return (
    <div className="signin-container">
      <h1>Sign In</h1>
      <form className='form' onSubmit={handleSubmit}>
        <div className='input-field' >
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="example@example.com"
          />
        </div>
        <div className='input-field'>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="Password"
          />
        </div>
        {error && <div style={{ color: 'red' }}>{error}</div>}
        <button className='submit-button' type="submit">Sign In</button>
      </form>
    </div>
  );
}

export default SignIn;