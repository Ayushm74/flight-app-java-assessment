import { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/flight";

function FlightList({ refresh, reloadFlights }) {
  const [flights, setFlights] = useState([]);

  useEffect(() => {
    loadFlights();
  }, [refresh]);

  function loadFlights() {
    fetch(API_URL + "/all")
      .then((response) => response.json())
      .then((data) => setFlights(data))
      .catch(() => setFlights([]));
  }

  function deleteFlight(code) {
    fetch(API_URL + "/delete/" + code, {
      method: "DELETE"
    })
      .then(() => {
        alert("Flight deleted successfully");
        reloadFlights();
      })
      .catch(() => {
        alert("Backend is not connected. Start Spring Boot on port 8080.");
      });
  }

  return (
    <section className="card">
      <h2>All Flights</h2>
      
      {flights.length === 0 ? (
        <p>No flights found</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Code</th>
              <th>Carrier</th>
              <th>Source</th>
              <th>Destination</th>
              <th>Cost</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {flights.map((flight) => (
              <tr key={flight.code}>
                <td>{flight.code}</td>
                <td>{flight.carrier}</td>
                <td>{flight.source}</td>
                <td>{flight.destination}</td>
                <td>{flight.cost}</td>
                <td>
                  <button onClick={() => deleteFlight(flight.code)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}

export default FlightList;
