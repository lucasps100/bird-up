export default function Success({ msg }) {
  if (!msg || msg === "") {
    return null;
  }

  return (
    <div className="alert alert-success">
      <h5>Success!</h5>
      <p>{msg}</p>
    </div>
  );
}
