import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePortfolioComponent } from './create-portfolio.component';

describe('CreatePortfolioComponent', () => {
  let component: CreatePortfolioComponent;
  let fixture: ComponentFixture<CreatePortfolioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatePortfolioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreatePortfolioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
