/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InsightTestModule } from '../../../test.module';
import { DegreeComponent } from 'app/entities/degree/degree.component';
import { DegreeService } from 'app/entities/degree/degree.service';
import { Degree } from 'app/shared/model/degree.model';

describe('Component Tests', () => {
  describe('Degree Management Component', () => {
    let comp: DegreeComponent;
    let fixture: ComponentFixture<DegreeComponent>;
    let service: DegreeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [DegreeComponent],
        providers: []
      })
        .overrideTemplate(DegreeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DegreeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Degree('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.degrees[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
