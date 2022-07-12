import React, { Component } from 'react';
import PersonDetails from "./PersonDetails";
import AddressDetails from "./AddressDetails";
import Confirmation from "./Confirmation";

class RegistrationForm extends Component {
  state = {
    step: 1,
    firstName: '',
    lastName: '',
    email: '',
    npi: '',
    address: '',
    city: '',
    state: '',
    zip: '',
  }

  nextStep = () => {
    const { step } = this.state
    this.setState({
        step : step + 1
    })
  }

  prevStep = () => {
      const { step } = this.state
      this.setState({
          step : step - 1
      })
  }

  handleChange = (event) => {
      this.setState({[event.target.name]: event.target.value})
  }

  render() {
    const { step, firstName, lastName, email, npi, address, city, state, zip } = this.state;
    const inputValues = { firstName, lastName, email, npi, address, city, state, zip };
    switch(step) {
      case 1:
          return <PersonDetails
                  nextStep={this.nextStep}
                  handleChange = {this.handleChange}
                  inputValues={inputValues}
                  />
      case 2:
          return <AddressDetails
                  nextStep={this.nextStep}
                  prevStep={this.prevStep}
                  handleChange = {this.handleChange}
                  inputValues={inputValues}
                  />
      case 3:
          return <Confirmation
                  nextStep={this.nextStep}
                  prevStep={this.prevStep}
                  inputValues={inputValues}
                  />
      }
  }
}

export default RegistrationForm;