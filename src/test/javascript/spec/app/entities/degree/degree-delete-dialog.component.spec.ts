/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InsightTestModule } from '../../../test.module';
import { DegreeDeleteDialogComponent } from 'app/entities/degree/degree-delete-dialog.component';
import { DegreeService } from 'app/entities/degree/degree.service';

describe('Component Tests', () => {
  describe('Degree Management Delete Component', () => {
    let comp: DegreeDeleteDialogComponent;
    let fixture: ComponentFixture<DegreeDeleteDialogComponent>;
    let service: DegreeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InsightTestModule],
        declarations: [DegreeDeleteDialogComponent]
      })
        .overrideTemplate(DegreeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DegreeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreeService);
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
