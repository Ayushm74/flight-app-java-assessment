import { useState } from "react";

const API_URL = "http://localhost:8080/flight";

function FlightForm({ reloadFlights, navigateTo }) {
  const [flight, setFlight] = useState({
    code: "",
    carrier: "",
    source: "",
    destination: "",
    cost: ""
  });

  function handleChange(event) {
    setFlight({ ...flight, [event.target.name]: event.target.value });
  }

  function addFlight(event) {
    event.preventDefault();

    fetch(API_URL + "/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(flight)
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Backend error");
        }
        return response.json();
      })
      .then(() => {
        alert("Flight added successfully");
        setFlight({
          code: "",
          carrier: "",
          source: "",
          destination: "",
          cost: ""
        });
        reloadFlights();
        if (navigateTo) {
          navigateTo("flights");
        }
      })
      .catch(() => {
        alert("Backend is not connected. Start Spring Boot on port 8080.");
      });
  }

  return (
    <section className="card">
      <h2>Add Flight</h2>
      <form onSubmit={addFlight}>
        <input name="code" value={flight.code} onChange={handleChange} placeholder="Flight Code" required />
        <input name="carrier" value={flight.carrier} onChange={handleChange} placeholder="Carrier" required />
        <input name="source" value={flight.source} onChange={handleChange} placeholder="Source" required />
        <input name="destination" value={flight.destination} onChange={handleChange} placeholder="Destination" required />
        <input name="cost" type="number" value={flight.cost} onChange={handleChange} placeholder="Cost" required />
        <button type="submit">Add Flight</button>
      </form>
    </section>
  );
}

export default FlightForm;
