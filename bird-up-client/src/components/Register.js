import { useState } from "react";
import { Link } from "react-router-dom";
import { register } from "../services/authAPI";
import { useContext } from "react";
import CreateProfileForm from "./CreateProfileForm";
import ValidationSummary from "./ValidationSummary";
import AuthContext from "../context/AuthContext";
import { login } from "../services/authAPI";

function Register() {
  const [errors, setErrors] = useState([]);
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const { handleLoggedIn } = useContext(AuthContext);
  const [success, setSuccess] = useState(false);

  const handleChange = (evt) => {
    const nextCredentials = { ...credentials };
    nextCredentials[evt.target.name] = evt.target.value;
    setCredentials(nextCredentials);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    setErrors([]);
    if (!validateForm()) {
      setErrors(["Passwords do not match!"]);
      return;
    }
    register(credentials).then((data) => {
      if (data && data.errors) {
        setErrors(data.errors);
      } else {
        login(credentials)
          .then((user) => {
            handleLoggedIn(user);
            setSuccess(true);
          })
          .catch((err) => {
            setErrors(["Invalid username/password."]);
          });
      }
    });
  };

  const validateForm = () => {
    return credentials.password === credentials.confirmPassword;
  };

  return (
    <div>
      <ValidationSummary errors={errors} />
      {success ? (
        <CreateProfileForm />
      ) : (
        <form onSubmit={handleSubmit}>
          <div>
            <div className="form-group">
              <label htmlFor="label">Username</label>
              <input
                type="text"
                className="form-control"
                id="username"
                name="username"
                value={credentials.username}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="label">Password</label>
              <input
                type="password"
                className="form-control"
                id="password"
                name="password"
                value={credentials.password}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="label">Confirm password</label>
              <input
                type="password"
                className="form-control"
                id="confirmPassword"
                name="confirmPassword"
                value={credentials.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <Link to="/" className="btn btn-secondary">
                Cancel
              </Link>
              <button type="submit" className="btn btn-primary">
                Sign up
              </button>
            </div>
          </div>
        </form>
      )}
    </div>
  );
}

export default Register;
