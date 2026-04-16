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

    public EnderecoDTO paraEnderecoDTO ( Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .bairro(endereco.getBairro())
                .numero(endereco.getNumero())
                .build();
    }

    public List<TelefoneDTO> paraListTelefoneDTO(List<Telefone> telefoneDTOS){
        return  telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario (UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefone(entity.getTelefone())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua(): entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero(): entity.getNumero())
                .bairro(dto.getBairro() != null ? dto.getBairro(): entity.getBairro())
                .complemento(dto.getComplemento() != null ? dto.getComplemento(): entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade(): entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado(): entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep(): entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO  dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }

}
