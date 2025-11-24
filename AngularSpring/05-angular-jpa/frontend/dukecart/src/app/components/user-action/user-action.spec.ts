import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAction } from './user-action';

describe('UserAction', () => {
  let component: UserAction;
  let fixture: ComponentFixture<UserAction>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserAction]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAction);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
