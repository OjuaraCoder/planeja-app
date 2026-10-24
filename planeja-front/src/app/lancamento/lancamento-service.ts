import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { DadosLancamentoForm, DetalhesLancamento } from './lancamento-dados';
import { Observable } from 'rxjs';
import { DetalhesCategoria } from '../categoria/categoria-dados';


@Injectable({
  providedIn: 'root',
})
export class LancamentoService {
  http = inject(HttpClient);
  baseURL = 'http://localhost:8080/lancamentos';

  criar(dados: DadosLancamentoForm): Observable<DetalhesLancamento> {
    return this.http.post<DetalhesLancamento>(this.baseURL, dados);
  }

  listarCategoriasDisponiveis(): Observable<DetalhesCategoria[]> {
    return this.http.get<DetalhesCategoria[]>(`${this.baseURL}/categorias-ativas`);
  }

  listarCartoesDisponiveis(): Observable<DetalhesCategoria[]> {
    return this.http.get<DetalhesCategoria[]>(`${this.baseURL}/cartoes-ativos`);
  }
}
