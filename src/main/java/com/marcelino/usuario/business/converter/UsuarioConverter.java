package com.marcelino.usuario.business.converter;

import com.marcelino.usuario.business.dto.EnderecoDTO;
import com.marcelino.usuario.business.dto.TelefoneDTO;
import com.marcelino.usuario.business.dto.UsuarioDTO;
import com.marcelino.usuario.infrastructure.entity.Endereco;
import com.marcelino.usuario.infrastructure.entity.Telefone;
import com.marcelino.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario (UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListEndereco(usuarioDTO.getEnderecos()))
                .telefone(paraListTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListEndereco(List<EnderecoDTO> enderecoDTOS){
        return  enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco ( EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .bairro(enderecoDTO.getBairro())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<Telefone> paraListTelefone(List<TelefoneDTO> telefoneDTOS){
        return  telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }



    //convertor de dto para entety


    public UsuarioDTO paraUsuarioDTO (Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListTelefoneDTO(usuarioDTO.getTelefone()))
                .build();
    }

    public List<EnderecoDTO> paraListEnderecoDTO(List<Endereco> enderecoDTOS){
        return  enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO ( Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .bairro(enderecoDTO.getBairro())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<TelefoneDTO> paraListTelefoneDTO(List<Telefone> telefoneDTOS){
        return  telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

}
