import { useEffect, useState, React} from "react";
import { Link, useNavigate } from "react-router-dom";
import Select from "react-select";

import {savePost} from "../services/postsAPI";
import { findAllSpecies } from "../services/speciesAPI";
import ValidationSummary from "./ValidationSummary";

const INITIAL_POST = {
    postId: 0,
    postText: "",
    image: null,
  };



export default function CreatePost() {
    const [post, setPost] = useState(INITIAL_POST);
    const [species, setSpecies] = useState([]);
    const [speciesId, setSpeciesId] = useState(0);

    const [errors, setErrors] = useState([]); 

    const navigate = useNavigate();

    useEffect(() => {
        findAllSpecies().then(setSpecies);
        console.log(species);
    }, [])

    const handleChange = (evt) => {
        if (evt.target.name === "image") {
            const nextPost = { ...post };
            nextPost[evt.target.name] = evt.target.files[0];
            setPost(nextPost);
        } 
        else {
            const nextPost = { ...post };
            nextPost[evt.target.name] = evt.target.value;
            setPost(nextPost);
        }
      };

      const handleSelectChange= (value, action)  => {
        setSpeciesId(value);
      };


      const handleSubmit = (evt) => {
        evt.preventDefault();
        savePost(post).then((data) => {
          if (data?.errors) {
            setErrors(data.errors);
          } else {
            navigate("/", {
              state: { message: `Your image was posted.` },
            });
          }
        });
      };
    

    

      return (
        <div>
          <ValidationSummary errors={errors} />
          <h2 className="modal-title">
                "New Post"
              </h2>
              {post.image && (
        <div>
          <img
            alt="not found"
            width={"250px"}
            src={URL.createObjectURL(post.image)}
          />
        </div>
      )}
          <form onSubmit={handleSubmit}>
            <div>
                <div className="form-row">
                    <div className="col form-group">
                    <input
                    type="file"
                    name="image"
                    onChange={handleChange} />
                    </div>

                </div>
            
              <div className="form-row">
                <div className="col form-group">
                  <label htmlFor="speciesId">Species</label>

                <Select required className="form-control" name="speciesId"
                onChange={handleSelectChange} value={speciesId}
                options={species.map(s => ({"label": s.speciesShortName, "value": s.speciesId}))}>

                </Select>

                </div>
              </div>
              <div className="form-group">
                <label htmlFor="postText">Description</label>
                <textarea
                  className="form-control"
                  name="postText"
                  id="postText"
                  value={post.postText}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <Link to="/" className="btn btn-secondary">
                  Cancel
                </Link>
                <button type="submit" className="btn btn-primary">
                  Save changes
                </button>
              </div>
            </div>
          </form>
        </div>
      );








}