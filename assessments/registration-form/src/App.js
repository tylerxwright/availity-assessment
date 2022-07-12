import 'bootstrap/dist/css/bootstrap.min.css';
import RegistrationForm from "./components/RegistrationForm";

import './App.css';
import fakeHeader from "./assets/availity.jpg";

function App() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={fakeHeader} className="App-header-img" />
        </header>
        <RegistrationForm />
      </div>
    );   
}

export default App;
