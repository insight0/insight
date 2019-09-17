/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InsightTestModule } from '../../../test.module';
import { CertificationComponent } from 'app/entities/certification/certification.component';
import { CertificationService } from 'app/entities/certification/certification.service';
import { Certification } from 'app/shared/model/certification.model';

describe('Component Tests', () => {
  describe('Certification Management Component', () => {
    let comp: CertificationComponent;
    let fixture: ComponentFixture<CertificationComponent>;
    let service: CertificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [CertificationComponent],
        providers: []
      })
        .overrideTemplate(CertificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CertificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CertificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Certification('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.certifications[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
