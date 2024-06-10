import { Component } from '@angular/core';
import { PortfolioService } from '../../../services/portfolio.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-portfolio',
  templateUrl: './create-portfolio.component.html'
})
export class CreatePortfolioComponent {

  createForm: FormGroup;

  constructor(private portfolioService: PortfolioService, private fb: FormBuilder) {
    this.createForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      const { name } = this.createForm.value;
      this.portfolioService.createPortfolio(name).subscribe(response => {
        alert('Portfolio created successfully!');
       });
    }
  }
}
