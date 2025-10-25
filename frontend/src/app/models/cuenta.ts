export interface Cuenta {
  id?: number;
  codigo: string;
  nombre: string;
  tipo?: string; // coincide con AccountType en backend (string)
  naturaleza?: string; // coincide con Naturaleza en backend (string)
}
