/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InsightTestModule } from '../../../test.module';
import { FunctionalRoleDetailComponent } from 'app/entities/functional-role/functional-role-detail.component';
import { FunctionalRole } from 'app/shared/model/functional-role.model';

describe('Component Tests', () => {
  describe('FunctionalRole Management Detail Component', () => {
    let comp: FunctionalRoleDetailComponent;
    let fixture: ComponentFixture<FunctionalRoleDetailComponent>;
    const route = ({ data: of({ functionalRole: new FunctionalRole('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [FunctionalRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FunctionalRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FunctionalRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.functionalRole).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
