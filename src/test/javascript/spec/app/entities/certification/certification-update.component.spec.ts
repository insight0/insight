/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { InsightTestModule } from '../../../test.module';
import { CertificationUpdateComponent } from 'app/entities/certification/certification-update.component';
import { CertificationService } from 'app/entities/certification/certification.service';
import { Certification } from 'app/shared/model/certification.model';

describe('Component Tests', () => {
  describe('Certification Management Update Component', () => {
    let comp: CertificationUpdateComponent;
    let fixture: ComponentFixture<CertificationUpdateComponent>;
    let service: CertificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [CertificationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CertificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CertificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CertificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Certification('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Certification();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
