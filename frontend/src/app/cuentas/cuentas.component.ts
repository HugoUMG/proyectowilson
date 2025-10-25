import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CuentasService } from '../services/cuentas.service';
import { Cuenta } from '../models/cuenta';

@Component({
  selector: 'app-cuentas',
  templateUrl: './cuentas.component.html',
  styleUrls: ['./cuentas.component.css'],
  providers: [CuentasService],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class CuentasComponent implements OnInit {
  cuentas: Cuenta[] = [];
  error: string | null = null;
  model: Partial<Cuenta> = {};

  constructor(private svc: CuentasService) {}

  ngOnInit(): void {
    console.log('CuentasComponent inicializado');
    this.load();
  }

  load() {
    console.log('Cargando cuentas...');
    this.svc.list().subscribe({
      next: data => { 
        console.log('Cuentas recibidas:', data); 
        this.cuentas = data; 
        this.error = null; 
      },
      error: err => { 
        console.error('Error cargando cuentas:', err);
        this.error = 'No se pudieron cargar las cuentas'; 
      }
    });
  }

  save() {
    if (this.model.id) {
      this.svc.update(this.model.id, this.model).subscribe({
        next: () => { this.reset(); this.load(); },
        error: e => { this.error = 'Error actualizando cuenta'; console.error(e); }
      });
    } else {
      this.svc.create(this.model).subscribe({
        next: () => { this.reset(); this.load(); },
        error: e => { this.error = 'Error creando cuenta'; console.error(e); }
      });
    }
  }

  edit(c: Cuenta) {
    this.model = { ...c };
  }

  remove(id?: number) {
    if (!id) return;
    if (!confirm('¿Eliminar cuenta?')) return;
    this.svc.delete(id).subscribe({
      next: () => this.load(),
      error: e => { this.error = 'Error eliminando cuenta'; console.error(e); }
    });
  }

  reset() { this.model = {}; this.error = null; }
}
