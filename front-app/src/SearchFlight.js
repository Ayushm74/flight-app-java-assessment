import { useState } from "react";

const API_URL = "http://localhost:8080/flight";

function SearchFlight() {
  const [activeTab, setActiveTab] = useState("code"); 
  
  const [code, setCode] = useState("");
  const [carrier, setCarrier] = useState("");
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");
  const [min, setMin] = useState("");
  const [max, setMax] = useState("");
  
  const [flight, setFlight] = useState(null);
  const [flights, setFlights] = useState([]);
  const [message, setMessage] = useState("");

  function showFlights(data) {
    setFlight(null);
    setFlights(data);
    if (data.length === 0) {
      setMessage("No flights found");
    } else {
      setMessage("");
    }
  }

  function handleTabChange(tab) {
    setActiveTab(tab);
    setFlight(null);
    setFlights([]);
    setMessage("");
  }

  function searchFlight(event) {
    event.preventDefault();

    fetch(API_URL + "/code/" + code)
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          setFlight(JSON.parse(data));
          setFlights([]);
          setMessage("");
        } else {
          setFlight(null);
          setFlights([]);
          setMessage("Flight not found");
        }
      })
      .catch(() => {
        setFlight(null);
        setFlights([]);
        setMessage("Backend is not connected. Start Spring Boot on port 8080.");
      });
  }

  function searchByCarrier(event) {
    event.preventDefault();

    fetch(API_URL + "/carrier/" + carrier)
      .then((response) => response.json())
      .then((data) => showFlights(data))
      .catch(() => setMessage("Backend is not connected. Start Spring Boot on port 8080."));
  }

  function searchByRoutes(event) {
    event.preventDefault();

    fetch(API_URL + "/routes/" + source + "/" + destination)
      .then((response) => response.json())
      .then((data) => showFlights(data))
      .catch(() => setMessage("Backend is not connected. Start Spring Boot on port 8080."));
  }

  function searchByPrice(event) {
    event.preventDefault();

    fetch(API_URL + "/price/" + min + "/" + max)
      .then((response) => response.json())
      .then((data) => showFlights(data))
      .catch(() => setMessage("Backend is not connected. Start Spring Boot on port 8080."));
  }

  return (
    <section className="card">
      <h2>Search Flights</h2>
      
      <div className="search-tabs">
        <button 
          className={`tab-btn ${activeTab === "code" ? "active" : ""}`}
          onClick={() => handleTabChange("code")}
        >
          By Code
        </button>
        <button 
          className={`tab-btn ${activeTab === "carrier" ? "active" : ""}`}
          onClick={() => handleTabChange("carrier")}
        >
          By Carrier
        </button>
        <button 
          className={`tab-btn ${activeTab === "route" ? "active" : ""}`}
          onClick={() => handleTabChange("route")}
        >
          By Route
        </button>
        <button 
          className={`tab-btn ${activeTab === "price" ? "active" : ""}`}
          onClick={() => handleTabChange("price")}
        >
          By Price
        </button>
      </div>

      <div style={{ marginTop: "15px" }}>
        {activeTab === "code" && (
          <form onSubmit={searchFlight}>
            <input value={code} onChange={(event) => setCode(event.target.value)} placeholder="Enter Flight Code" required />
            <button type="submit">Search By Code</button>
          </form>
        )}

        {activeTab === "carrier" && (
          <form onSubmit={searchByCarrier}>
            <input value={carrier} onChange={(event) => setCarrier(event.target.value)} placeholder="Enter Carrier" required />
            <button type="submit">Search By Carrier</button>
          </form>
        )}

        {activeTab === "route" && (
          <form onSubmit={searchByRoutes}>
            <input value={source} onChange={(event) => setSource(event.target.value)} placeholder="Enter Source" required />
            <input value={destination} onChange={(event) => setDestination(event.target.value)} placeholder="Enter Destination" required />
            <button type="submit">Search By Route</button>
          </form>
        )}

        {activeTab === "price" && (
          <form onSubmit={searchByPrice}>
            <input type="number" value={min} onChange={(event) => setMin(event.target.value)} placeholder="Minimum Cost" required />
            <input type="number" value={max} onChange={(event) => setMax(event.target.value)} placeholder="Maximum Cost" required />
            <button type="submit">Search By Price</button>
          </form>
        )}
      </div>

      {message && <p className="message">{message}</p>}

      {flight && (
        <div className="result">
          <p><b>Code:</b> {flight.code}</p>
          <p><b>Carrier:</b> {flight.carrier}</p>
          <p><b>Source:</b> {flight.source}</p>
          <p><b>Destination:</b> {flight.destination}</p>
          <p><b>Cost:</b> {flight.cost}</p>
        </div>
      )}

      {flights.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Code</th>
              <th>Carrier</th>
              <th>Source</th>
              <th>Destination</th>
              <th>Cost</th>
            </tr>
          </thead>
          <tbody>
            {flights.map((item) => (
              <tr key={item.code}>
                <td>{item.code}</td>
                <td>{item.carrier}</td>
                <td>{item.source}</td>
                <td>{item.destination}</td>
                <td>{item.cost}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}

export default SearchFlight;
