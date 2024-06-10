import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Portfolio } from '../models/portfolio';


const URL = 'http://localhost:8080/api/portfolios'; 

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {



  constructor(private http: HttpClient) { }

  createPortfolio(name: string): Observable<any> {
    return this.http.post(`${URL}/createPortfolio`, { name });
  }

  deletePortfolio(portfolioId: number): Observable<void> {
    return this.http.delete<void>(`${URL}/${portfolioId}`);
  }

  getPortfoliosByUser(): Observable<Portfolio[]> {
    return this.http.get<Portfolio[]>(`${URL}/user`,{
      withCredentials: true,
    });
  }

  getPortfolioByUserAndId(portfolioId: number): Observable<Portfolio> {
    return this.http.get<Portfolio>(`${URL}/${portfolioId}`);
  }
}
