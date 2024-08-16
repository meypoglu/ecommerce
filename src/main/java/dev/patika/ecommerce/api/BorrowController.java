package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IBorrowService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.borrow.BorrowSaveRequest;
import dev.patika.ecommerce.dto.request.borrow.BorrowUpdateRequest;
import dev.patika.ecommerce.dto.response.borrow.BorrowResponse;
import dev.patika.ecommerce.entities.Borrow;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/borrows")
public class BorrowController {
    private final IBorrowService borrowService;
    private final IModelMapperService modelMapper;

    public BorrowController(IBorrowService borrowService, IModelMapperService modelMapper) {
        this.borrowService = borrowService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BorrowResponse> save(@Valid @RequestBody BorrowSaveRequest borrowSaveRequest) {
        Borrow saveBorrow = this.modelMapper.forRequest().map(borrowSaveRequest, Borrow.class);
        this.borrowService.save(saveBorrow);
        BorrowResponse borrowResponse = this.modelMapper.forResponse().map(saveBorrow, BorrowResponse.class);
        return ResultHelper.created(borrowResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BorrowResponse> get(@PathVariable("id") int id) {
        Borrow borrow = this.borrowService.get(id);
        BorrowResponse borrowResponse = this.modelMapper.forResponse().map(borrow, BorrowResponse.class);
        return ResultHelper.success(borrowResponse);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BorrowResponse> update(@Valid @RequestBody BorrowUpdateRequest borrowUpdateRequest) {
        Borrow updateBorrow = this.modelMapper.forRequest().map(borrowUpdateRequest, Borrow.class);
        this.borrowService.update(updateBorrow);
        BorrowResponse borrowResponse = this.modelMapper.forResponse().map(updateBorrow, BorrowResponse.class);
        return ResultHelper.success(borrowResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.borrowService.delete(id);
        return ResultHelper.ok();
    }
}
