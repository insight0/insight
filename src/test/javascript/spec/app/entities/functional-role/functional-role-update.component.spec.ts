/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { InsightTestModule } from '../../../test.module';
import { FunctionalRoleUpdateComponent } from 'app/entities/functional-role/functional-role-update.component';
import { FunctionalRoleService } from 'app/entities/functional-role/functional-role.service';
import { FunctionalRole } from 'app/shared/model/functional-role.model';

describe('Component Tests', () => {
  describe('FunctionalRole Management Update Component', () => {
    let comp: FunctionalRoleUpdateComponent;
    let fixture: ComponentFixture<FunctionalRoleUpdateComponent>;
    let service: FunctionalRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [FunctionalRoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FunctionalRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FunctionalRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FunctionalRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FunctionalRole('123');
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
        const entity = new FunctionalRole();
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
