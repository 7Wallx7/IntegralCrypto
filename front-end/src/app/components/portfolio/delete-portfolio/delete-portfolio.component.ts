import { Component } from '@angular/core';
import { PortfolioService } from '../../../services/portfolio.service';

@Component({
  selector: 'app-delete-portfolio',
  templateUrl: './delete-portfolio.component.html'
})
export class DeletePortfolioComponent {

  portfolioId: number=0;

  constructor(private portfolioService: PortfolioService) { }

  deletePortfolio(): void {
    this.portfolioService.deletePortfolio(this.portfolioId).subscribe(() => {
      alert('Portfolio deleted successfully!');
    });
  }
}