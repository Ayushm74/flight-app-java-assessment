import { useState } from "react";
import FlightForm from "./FlightForm";
import FlightList from "./FlightList";
import SearchFlight from "./SearchFlight";

function App() {
  const [refresh, setRefresh] = useState(0);
  const [currentPage, setCurrentPage] = useState("flights");

  function reloadFlights() {
    setRefresh((prev) => prev + 1);
  }

  function handleNavigate(page) {
    setCurrentPage(page);
  }

  return (
    <div>
      <nav className="navbar">
        <h1>Flight Management</h1>

        <ul className="nav-menu">
          <li>
            <button 
              className={`nav-btn ${currentPage === "flights" ? "active" : ""}`}
              onClick={() => handleNavigate("flights")}
            >
              All Flights
            </button>
          </li>
          <li>
            <button 
              className={`nav-btn ${currentPage === "add-flight" ? "active" : ""}`}
              onClick={() => handleNavigate("add-flight")}
            >
              Add Flight
            </button>
          </li>
          <li>
            <button 
              className={`nav-btn ${currentPage === "search" ? "active" : ""}`}
              onClick={() => handleNavigate("search")}
            >
              Search Flights
            </button>
          </li>
        </ul>
      </nav>

      <main className="container">
        {currentPage === "flights" && (
          <FlightList refresh={refresh} reloadFlights={reloadFlights} />
        )}
        {currentPage === "add-flight" && (
          <FlightForm reloadFlights={reloadFlights} navigateTo={handleNavigate} />
        )}
        {currentPage === "search" && (
          <SearchFlight />
        )}
      </main>
    </div>
  );
}

export default App;
