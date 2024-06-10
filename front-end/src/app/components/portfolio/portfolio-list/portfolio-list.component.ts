import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../../../services/portfolio.service';
import { Portfolio } from '../../../models/portfolio';
import { StorageService } from '../../../_services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html'
})
export class PortfolioListComponent implements OnInit {

  portfolios: Portfolio[] = [];

  constructor(private portfolioService: PortfolioService, private storageService: StorageService, private router: Router) { }

  ngOnInit(): void {
    if (!this.storageService.isLoggedIn()) {
      this.router.navigate(['/login']);
    } else {
      this.getPortfolios();
    }
  }

  getPortfolios(): void {
    this.portfolioService.getPortfoliosByUser().subscribe(data => {
      this.portfolios = data;
    }, err => {
      if (err.status === 401) {
        this.router.navigate(['/login']);
      }
    });
  }
}
