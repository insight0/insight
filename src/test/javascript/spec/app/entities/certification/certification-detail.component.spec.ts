/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InsightTestModule } from '../../../test.module';
import { CertificationDetailComponent } from 'app/entities/certification/certification-detail.component';
import { Certification } from 'app/shared/model/certification.model';

describe('Component Tests', () => {
  describe('Certification Management Detail Component', () => {
    let comp: CertificationDetailComponent;
    let fixture: ComponentFixture<CertificationDetailComponent>;
    const route = ({ data: of({ certification: new Certification('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [CertificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CertificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CertificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.certification).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
