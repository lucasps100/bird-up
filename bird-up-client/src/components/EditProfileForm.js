import { useState, useEffect, useParams, useContext } from "react";
import { editProfile } from "../services/profileAPI";
import ValidationSummary from "./ValidationSummary";
import { useNavigate } from "react-router-dom";
import { getProfileByUsername } from "../services/profileAPI";
import AuthContext from "../context/AuthContext";

export default function EditProfileForm() {
  const { errors, setErrors } = useState([]);
  const navigate = useNavigate();

  const { user } = useContext(AuthContext);

  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [bio, setBio] = useState();

  useEffect(() => {
    getProfileByUsername(user.username)
      .then((p) => {
        setFirstName(p.firstName);
        setLastName(p.lastName);
        setBio(p.bio);
      })
      .catch((err) =>
        navigate("/error", {
          state: { message: err },
        })
      );
  }, []);

  const handleChange = (evt) => {
    if (evt.target.name === "fistName") {
      setFirstName(evt.target.value);
    } else if (evt.target.name === "lastName") {
      setLastName(evt.target.value);
    } else {
      setBio(evt.target.value);
    }
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    const profile = {
      firstName,
      lastName,
      bio,
    };
    editProfile(profile).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
      } else {
        navigate("/");
      }
    });
  };

  return (
    <div>
      <ValidationSummary errors={errors} />

      <form onSubmit={handleSubmit} className="mx-3">
        <div>
          <h2 className="modal-title">New Profile</h2>
          <div className="form-group">
            <label htmlFor="firstName">First Name:</label>
            <input
              className="form-control"
              type="text"
              name="firstName"
              id="firstName"
              value={firstName}
              onChange={handleChange}
            />
          </div>
          <div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name:</label>
              <input
                className="form-control"
                type="text"
                name="lastName"
                id="lastName"
                value={lastName}
                onChange={handleChange}
              />
            </div>
            <div className="row form-group">
              <label htmlFor="bio">About You:</label>
              <textarea
                className="form-control"
                type="text"
                name="bio"
                rows="10"
                id="bio"
                value={bio}
                onChange={handleChange}
              />
            </div>
          </div>
          <div>
            <button type="submit" className="btn btn-primary">
              Save Changes
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
