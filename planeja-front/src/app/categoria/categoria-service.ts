import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCategoriaForm, DetalhesCategoria } from './categoria-dados';
import { Observable } from 'rxjs';
import { PageResult } from '../common/pagination/page-result';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {
  private http = inject(HttpClient);
  baseUrl: string = 'http://localhost:8080/categorias';

  criarCategoria(dados: DadosCategoriaForm): Observable<DetalhesCategoria> {
    return this.http.post<DetalhesCategoria>(this.baseUrl, dados);
  }

  listarCategorias(page: number = 0, size: number = 5): Observable<PageResult<DetalhesCategoria>> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get<PageResult<DetalhesCategoria>>(url);
  }

  obterPorId(id: string): Observable<DetalhesCategoria> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<DetalhesCategoria>(url);
  }

  atualizarCategoria(id: string, dados: DadosCategoriaForm): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<void>(url, dados);
  }

  mudarStatus(id: string):Observable<void>{
    const url = `${this.baseUrl}/${id}/status`;
    return this.http.patch<void>(url, {});
  }

}
