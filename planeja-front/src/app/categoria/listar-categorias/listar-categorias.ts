import { Component, OnInit, inject } from '@angular/core';
import { CategoriaService } from '../categoria-service';
import { PageResult } from '../../common/pagination/page-result';
import { DetalhesCategoria } from '../categoria-dados';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Header } from '../../common/components/header/header';

@Component({
  selector: 'app-listar-categorias',
  imports: [CommonModule, RouterModule, Header],
  templateUrl: './listar-categorias.html',
  styleUrl: './listar-categorias.scss',
})
export class ListarCategorias implements OnInit {
  service = inject(CategoriaService);
  router = inject(Router);
  toast = inject(ToastrService);
  listagem$!: Observable<PageResult<DetalhesCategoria>>;
  paginaAtual = 0;
  tamanhoPagina = 5;

  ngOnInit(): void {
    this.listarCategorias();
  }

  listarCategorias(): void {
    this.listagem$ = this.service.listarCategorias(this.paginaAtual, this.tamanhoPagina);
  }

  navegar(pagina: number): void {
    this.paginaAtual = pagina;
    this.listarCategorias();
  }

  navegarProximo(listagem: PageResult<DetalhesCategoria>) {
    if (!listagem.last) {
      this.navegar(listagem.number + 1);
    }
  }

  navegarAnterior(listagem: PageResult<DetalhesCategoria>) {
    if (!listagem.first) {
      this.navegar(listagem.number - 1);
    }
  }

  paginas(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (valor, index) => index);
  }

  registroInicial(listagem: PageResult<DetalhesCategoria>) {
    if (listagem.totalElements === 0) {
      return 0;
    }
    return listagem.number * listagem.size + 1;
  }

  registroFinal(listagem: PageResult<DetalhesCategoria>) {
    if (listagem.totalElements === 0) {
      return 0;
    }
    return Math.min((listagem.number + 1) * listagem.size, listagem.totalElements);
  }

  prepararEdicao(idCategoria: string) {
    this.router.navigate(['/cadastro-categoria'], { queryParams: { id: idCategoria } });
  }

  mudarStatus(idCategoria: string) {
    this.service.mudarStatus(idCategoria).subscribe((next) => {
      this.toast.success('Registro atualizado com sucesso');
      this.listarCategorias();
    });
  }
}
