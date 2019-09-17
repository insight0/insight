/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InsightTestModule } from '../../../test.module';
import { CertificationDeleteDialogComponent } from 'app/entities/certification/certification-delete-dialog.component';
import { CertificationService } from 'app/entities/certification/certification.service';

describe('Component Tests', () => {
  describe('Certification Management Delete Component', () => {
    let comp: CertificationDeleteDialogComponent;
    let fixture: ComponentFixture<CertificationDeleteDialogComponent>;
    let service: CertificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [CertificationDeleteDialogComponent]
      })
        .overrideTemplate(CertificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CertificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CertificationService);
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
