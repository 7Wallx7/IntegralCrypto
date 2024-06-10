import { Component } from '@angular/core';
import { PortfolioService } from '../../../services/portfolio.service';
import { Portfolio } from '../../../models/portfolio';

@Component({
  selector: 'app-portfolio-detail',
  templateUrl: './portfolio-detail.component.html'
})
export class PortfolioDetailComponent {

  portfolio?: Portfolio;
  portfolioId: number = 0;

  constructor(private portfolioService: PortfolioService) { }

  getPortfolio(): void {
    this.portfolioService.getPortfolioByUserAndId(this.portfolioId).subscribe(data => {
      this.portfolio = data;
    });
  }
}
