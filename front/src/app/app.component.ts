import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { EmployeeService } from './employee.service';
import { Employee } from './employee.models'; // Adjust the path if necessary
import { HttpErrorResponse } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [CommonModule],
  providers: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  public employees!: Employee[];

  constructor( private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.getEmployees();
  }

  title = 'front';

  public getEmployees() {
    this.employeeService.getEmployees().subscribe(
      (employees: Employee[]) => {
      this.employees = employees;
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
  }
}
