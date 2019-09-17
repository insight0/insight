/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InsightTestModule } from '../../../test.module';
import { FunctionalRoleDeleteDialogComponent } from 'app/entities/functional-role/functional-role-delete-dialog.component';
import { FunctionalRoleService } from 'app/entities/functional-role/functional-role.service';

describe('Component Tests', () => {
  describe('FunctionalRole Management Delete Component', () => {
    let comp: FunctionalRoleDeleteDialogComponent;
    let fixture: ComponentFixture<FunctionalRoleDeleteDialogComponent>;
    let service: FunctionalRoleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [FunctionalRoleDeleteDialogComponent]
      })
        .overrideTemplate(FunctionalRoleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FunctionalRoleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FunctionalRoleService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
